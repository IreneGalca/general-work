#Naive Bayes Classifier test
#tutorial used: https://rpubs.com/dbrown/nbclass

getwd()

#---------reads data from the CSV file------------------#
spam <- read.csv("spam.csv", stringsAsFactors = FALSE)
str(spam)

#================================Collecting data============================#
#distinguishes the type of the data (spam or ham)
spam$v1 <- factor(spam$v1)
str(spam$v1) #succcess

#-------------Cleaning and standardizing text data----------#
spam_corpus <- VCorpus(VectorSource(spam$text))
typeof(spam_corpus)
#just to show that it is a list

print(spam_corpus)#does not output properly.
#should be "Content: documents: 5572"



#----------------------Inspection---------------------------#
length(spam_corpus) %>%
sample(replace = FALSE) %>%
  sort.list(decreasing = FALSE) %>%
  head(2) %>%
  spam_corpus[.] %>%
  inspect()
#output doesn't work, possibly due to the errors from "spam_corpus"


wordStem(c("talk", "talking", "talked", "talks"))
