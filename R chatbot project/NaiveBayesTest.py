# -*- coding: utf-8 -*-
"""
Created on Thu Mar 12 09:10:15 2020

@author: kitty
"""

import pandas as pd
import nltk as n
from collections import Counter as ctr
import itertools as i 
import numpy as np

data = pd.read_csv('spam.csv',skiprows=1, names=['type','text', 'c3', 'c4', 'c5'], delimiter=',', encoding='ISO-8859-1')
data.drop(['c3','c4','c5'], axis=1, inplace=True)
smoother = .00001      #makes sure there is not a 0% chance of probability the word is in an email (see below)

set(data.type)
data['split'] = data.text.apply(lambda x: x.split())

#data[:5]   #Show first five entries of data

vocabulary = [w for s in data.split for w in s]
#len(vocabulary)   #How many words are in vocabulary
#len(set(vocabulary))      #How many words are in the set of vocabulary

### Estimate P(T)     #Training is in this section
test = data.sample(frac=0.1)
train = data[~data.index.isin(test.index)]

p_t_estimate = ctr(train.type)
p_t_total = len(train)
#p_t_estimate

def Pt(T):
    return p_t_estimate[T] / p_t_total      #Bayes Theorem 

#Pt('ham'), Pt('spam')        #Probability of ham and spam
    
###Estimate P(W)
words = [w for s in train.split for w in s]
p_w_estimate = ctr(words)
p_w_total = len(words)

#p_w_estimate['the']   #how many occurances of the word

def Pw(W):
    if W not in p_w_estimate:
        return smoother
    return p_w_estimate[W] / p_w_total

#Pw('the')           #Probability of the word
#np.sum([Pw(w) for w in set(words)])     #Probability should sum to one
    
###Estimate P(W|T)
p_w_t_estimate = {}
p_w_t_totals = {}

for t in set(train.type):
    sub_frame = train[train.type == t]
    sub_words = [w for s in sub_frame.split for w in s]
    #sub_frame[t] = ctr(sub_words)
    p_w_t_estimate[t] = ctr(sub_words)
    p_w_t_totals[t] = len(sub_words)
    
#sub_frame
#p_w_estimate['spam']['the']    #How many times spam has the word 'the'
    
#p_w_t_totals['spam']    #Total words in spam
    
def Pwt(W,T):
    if W not in p_w_t_estimate[T]:
        return smoother
    return p_w_t_estimate[T][W] / p_w_t_totals[T]

###Bayes' Rule

def Ptw(T,W):
    return Pwt(W,T)*Pt(T) / Pw(W)

#Pwt('the','spam')   #Probability of 'the' given spam
    
def Pe(E):
    result = {}
    for t in set(train.type):      #Assuming we have already split and lowercase emails
        result[t] = np.prod([Ptw(t, word) for word in E])
    return result

#Pe(['the','river','is','long'])         #Probability whether it is in ham or spam
#If ham or spam is 0, that means one of the words does not show up in spam
    
#len(test)       #length of the test data
test['result'] = test.split.apply(Pe)
test['top'] = test.result.apply(lambda x:max(x, key=x.get))
#print(p_w_t_estimate)
#sum(test.type == test.top) / len(test)     #accuracy score on test set
#accuracy depends on how "smooth" the data is... Bigger smoother equals less
#accurate, smaller equals more accurate (the majority of the time)
