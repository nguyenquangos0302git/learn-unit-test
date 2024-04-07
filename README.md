Practice:

#REAL-ESTATE-APP

RealEstateApp is a project stub for real estate agents. There are three classes available:

1. Apartment (with a given area and price).
2. ApartmentRater, which rates the price/area ratio for a given apartment. There are three possible ratings: 0 (best price/area ratio), 1 or 2 (worst price/area ratio).
3. RandomApartmentGenerator, which generates an apartment with a random price and area. If you invoke the constructor with no parameters, the default values are used: minimum area of 30.0 square meters and minimum price per square meter of 3000.0. You can also specify your own minimum area and price. In either case, the maximum values are: mimum values \* 4.0.

Here are my suggestions as to what tests you can write:

1. For ApartmentRater:
   a. should_ReturnCorrectRating_When_CorrectApartment -- write a parameterized test with different values
   b. should_ReturnErrorValue_When_IncorrectApartment
   c. should_CalculateAverageRating_When_CorrectApartmentList
   d. should_ThrowExceptionInCalculateAverageRating_When_EmptyApartmentList

2. For RandomApartmentGenerator (since values are randomly generated, repeat the tests multiple times):
   a. should_GenerateCorrectApartment_When_DefaultMinAreaMinPrice
   b. should_GenerateCorrectApartment_When_CustomMinAreaMinPrice

#ACTIVITY-LEVEL-APP
Should be apply Test Driven Development

Rate user's activity level (bad-average-good).
Input:
(1) the weekly time [min] spent doing cardio.
(2) the number of workout sessions (1 workout = 45 minutes). Throw exception when any input below 0.
Calculate the weekly total, divide by 7 to find the daily average.
If < 20, return "bad".
If >= 20 and < 40, return "average".
If >= 40, return "good".
