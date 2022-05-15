#install.packages("e1071", dep = TRUE)
#install.packages("tm")
#install.packages("gmodels")
#install.packages("SnowballC")
#install.packages("wordcloud")
#install.packages("broom")
#install.packages("quanteda")
#install.packages("tidytext")
#install.packages("hash")
library(quanteda)
library(tm)
library(SnowballC)
library(e1071)
library(gmodels)
library(dplyr)
library(stringr)
library(wordcloud)
library(broom)
library(tidytext)
require(data.table)
library(hash)

#!!!!!!!!!!!!!!!!!!!!!!!Change this line to your file path of the CSV!!!!!!!!!!!!!
sms_raw <- read.csv("C:/Users/kitty/Documents/School & Activities/Boise_State_University/Spring 2020/CS354/Final/7282_1_6.csv", stringsAsFactors = FALSE)

#data preprocessing--------------------
sms_raw$reviews.rating <- factor(sms_raw$reviews.rating)
str(sms_raw$reviews.rating) #Success! run
sms_stars <- factor(sms_raw$reviews.rating)
dataframe <- data.table(sms_raw)
dataframe <- dataframe[, reviews.text := tolower(reviews.text)]
dataframe <- dataframe[, reviews.text := removeNumbers(reviews.text)]
dataframe <- dataframe[, reviews.text := removePunctuation(reviews.text)]
dataframe <- dataframe[, reviews.text := stemDocument(reviews.text)]
dataframe <- dataframe[, reviews.text := stripWhitespace(reviews.text)]

#split the data up for testing
sms_dtm_train <- dataframe[1:20, ]#[1:14364, ]#split data 80/20 training/testing
sms_dtm_test <- dataframe[21:40, ]#[14365:17956, ]
sms_train_labels <- sms_raw[1:20, ]$reviews.rating##[1:14364, ]
sms_test_labels <- sms_raw[21:40, ]$reviews.rating#[14365:17956, ]
sms_train_labels %>%
  table %>%
  prop.table
sms_test_labels %>%
  table %>%
  prop.table

#sms_dtm_freq_train <- sms_dtm_train %>%#this will not run on data table, functionality to add in future
#  findFreqTerms(5) %>%
#  sms_dtm_train[ , .]
#sms_dtm_freq_test <- sms_dtm_test %>%
#  findFreqTerms(5) %>%
#  sms_dtm_test[ , .]

typeof(toString(sms_train_labels[[1]]))

starProb <- function(int) {#returns probability of a given star occuring 
  tableOfStars <- table(sms_train_labels)
  starCount <- tableOfStars[[int+1]]
  total <- sum(tableOfStars)
  prob <- (starCount/total)
  return(prob)
}
wordOccur <- tidy(sms_dtm_freq_train)#count occurances of a word in all training data
wordProb <- function(String) {#returns the probability of a given word occuring
  count <- count(wordOccur, toString(String))
  count <- count$n
  if(count == 0){
    return(.00001)
  }
  total <- sum(wordOccur[3])
  prob <- (count/total)#divide 
  return(prob)
}

wordProb("was")#returns probability of the word "was" occuring in total
wordWithStarProb <- function(String, int) {#find probability that a word will occur with a given star rating
  String = toString(String)
  filteredReviews <- sms_dtm_train[reviews.rating==int]#filter for just reviews of given star prob
  all_words <- unlist( # flattten word list from individual strings into one vector
    regmatches(filteredReviews$reviews.text,  gregexpr('\\w+', filteredReviews$reviews.text))) # extract all words
  freq_count <- table(all_words)# count frequencies
  wordOccurances <- freq_count[String]
  if(is.na(wordOccurances)){#if word is not found, return smoother
    return(.00001)
  }
  wordOccurances <- wordOccurances/sum(freq_count)#number of times word occurs in review divided by number of words in review
  return(wordOccurances)
}
#example <- wordWithStarProb("a", 2)#expected .0316455 this is the probability that a given word will occur for a given review.

#Bayes Rule
Ptw <- function(String, int){
  return(wordWithStarProb(String, int)*starProb(int)/wordProb(String))
}
Ptw("was", 3)#Expected: .01206554

counter <- 1
for(String in sms_dtm_test){
  counter = counter * Ptw(String ,1)
  print(counter)
}
probList[1][1] = counter

Pe <- function(){
  probList <- list()
  counter <- 1
  for(i in 1:6){
    for(String in sms_dtm_test){
      counter = counter * Ptw(String ,i-1)
    }
    probList[i][1] = counter
    counter = 1
  }
  return(probList)
}

Pe <- function(E){     
  probList <- list()     
  counter <- 1     
  for(i in 1:6){       
    for(String in E){         
      counter = counter * Ptw(String ,i-1)       
    }       
    probList[i][String] = counter       
    counter = 1     
  }     
  return(probList)   
}   
Pe(sms_dtm_test[1]) 
sms_dtm_test[1]$reviews.text
print(summary(sms_dtm_test))   

probOrder <- Pe()
probOrder
which.max(probOrder) - 1

Pe <- function(E){
  result <- hash()
  counter <- 1
  for(String in sms_dtm_test){      #Assuming we have already split and lowercase emails
    result[[String]] = Ptw(String,2)
  }
  return(result)
}

result <- hash()
counter <- 1
for(String in sms_dtm_test){      #Assuming we have already split and lowercase emails
  result[[String]] = Ptw(String,2)
}

for(String in sms_dtm_test){
  print(Ptw(String,2))
}

strsplit(sms_dtm_train$reviews.text, " ")[[i]]
unique(strsplit(sms_dtm_train$reviews.text, " "))[[1]]

count <- count(wordOccur, toString("was"))
count <- count$n
count

h <- hash() 
h[["3"]] <- 24
h[["3"]]
r <- hash()
r[["2"]] <- h
r[["2"]][["1"]]
r[["2"]][["3"]] <- r[["2"]][["3"]] + 34
r[["2"]][["2"]]
r[["2"]] <- h
r[["2"]]
h <- NULL
h[["1"]] <- 78
r[["2"]] <- h
clear(h)
r[["2"]]
r

wordHash <- hash()
index2 <- 1
h <-hash()
i <- 1
for (i in 1:20){
  l <- as.character(sms_train_labels[i]) 
  while (!is.na(unique(strsplit(sms_dtm_train$reviews.text, " "))[[i]][index2])){
    print(i)
    v <- as.character(unique(strsplit(sms_dtm_train$reviews.text, " "))[[i]][index2])
    c <- length(which(unique(strsplit(sms_dtm_train$reviews.text, " "))[[i]]==(strsplit(sms_dtm_train$reviews.text, " ")[[i]][[index2]]))) + 1
    if(!is.null(wordHash[[l]])){
      if(exists(v, wordHash[[l]])){
        wordHash[[l]][[v]] <- wordHash[[l]][[v]] + c
      } else{
        wordHash[[l]][[v]] <- c
      }
    } else{
      wordHash[[l]] <- hash()
      wordHash[[l]][[l]][[v]] <- c
    }
        index2 <- index2 + 1
  }
  index2 <- 1
}

wordHash
as.character(unique(strsplit(sms_dtm_train$reviews.text, " "))[[12]][2])
