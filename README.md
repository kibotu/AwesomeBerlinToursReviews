[![About Jan Rabe](https://img.shields.io/badge/about-me-green.svg)](https://about.me/janrabe)

# Awesome Berlin Tours Reviews [![Build Status](https://travis-ci.org/kibotu/AwesomeBerlinToursReviews.svg?branch=master)](https://travis-ci.org/kibotu/AwesomeBerlinToursReviews)  [![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15) [![Gradle Version](https://img.shields.io/badge/gradle-4.10.2-green.svg)](https://docs.gradle.org/current/release-notes) [![Kotlin](https://img.shields.io/badge/kotlin-1.3.0--rc--57-green.svg)](https://github.com/JetBrains/kotlin)  [![Licence](https://img.shields.io/badge/licence-MIT-blue.svg)](https://github.com/kibotu/AwesomeBerlinToursReviews/blob/master/LICENSE)

### The Challenge

    Create a Android app that allows a customer to browse reviews for one of our most popular Berlin tours.
    Feel free to add any feature that you feel relevant to the use case.
    The following web service delivers n (count) reviews which include the review author, title, message, date, rating, language code:

## CI

Develop 

### How to build

    gradlew build

## How to install

    gradlew installRelease
    
    
## How to install

    gradlew installRelease

### API

#### Reviews Request

    GET /berlin-l17/tempelhof-2-hour-airport-history-tour-berlin-airlift-more-t23776/reviews.json HTTP/1.1
    Host: www.getyourguide.com
    User-Agent: GetYourGuide

| Name | Type | Constraints | Description | Necessity |
| --------- | :-------: | :--------: | :--------: |:--------: |
| count | integer | | amount results | required |
| page | integer | | page number | required  |
| rating | integer | 0..5 |  review rating ||
| sortBy | string | date_of_review, rating |  sort criteria ||
| direction | string |  asc, desc | sort order ||

#### Response

ReviewResponse

| Name | Type | Description |
| --------- | :--------: |:--------: |
| status | bool | response status | 
| total_reviews_comments | integer | total count of review comments |   
| data | Review | review data |

Review

| Name | Type | Description |
| --------- | :--------: |:--------: |
| review_id | int | unique id |
| rating | string | review rating |
| title | string | review title |
| message | string | actual review message |
| author | string | name of reviewer plus reviewer country |
| foreignLanguage | bool | review data |
| date | string | formatted review creation date |
| date_unformatted | object | unformatted date |
| languageCode | string | language code |
| traveler_type | string | type of traveler, values: couples, family_old, group, solo |
| reviewerName | string | reviewer name |
| reviewerCountry | string | reviewer country |

#### Example

    curl -X GET \
      'https://www.getyourguide.com/berlin-l17/tempelhof-2-hour-airport-history-tour-berlin-airlift-more-t23776/reviews.json?count=5&page=0&rating=0&sortBy=date_of_review&direction=DESC' \
      -H 'User-Agent: GetYourGuide'

    {
      "status": true,
      "total_reviews_comments": 579,
      "data": [
        {
          "review_id": 3946905,
          "rating": "5.0",
          "title": "Imponerende bygningsværk",
          "message": "Spændende rundvisning i og ovenpå bygningen.\nRigtig god og vidende guide",
          "author": "Jette Vinther – Denmark",
          "foreignLanguage": true,
          "date": "October 8, 2018",
          "date_unformatted": {

          },
          "languageCode": "da",
          "traveler_type": "friends",
          "reviewerName": "Jette Vinther",
          "reviewerCountry": "Denmark"
        }
      ]
    }
    
### Implemented features

* splash screen
* app logo
* app bar
* collapsing toolbar with parallax scrolling
* endless scrolling using pagination
* lifecycle aware ui updates
* rx and retrofit for api requests 
* google translations of review messages and titles to potentially over 100 languages
* added wobbly bottomsheet for language selection
* review layout that displays rating, date, title, message, author name
* sorting by rating and date ascending and descending
* build pipeline with travis
* latest sdks and libraries used 
* added image optimizer plugin using pngquant 
* added readme documentation
* added license
* added animation for scrolling review list

#### ideas

* text to speech
* accessibility support
* filter by traveler type
* error-tracking
* analytics
* tests
* user interaction
    * rating of comments (how? by user usage or buttons?)
        *  improving relevance by tracking user usage
    * commenting on comments? 'allow comments'? 
    * guidelines for commenting?
    * checkbox to flag review as question
    * notifications on comment interaction
* offline caching
* action.view support for indexing  
* online/offline cache
* instant app for comments
* connectivity handling
* more meaningful error handling