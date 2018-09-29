# Project description
Project requested by Principal Engineering on behalf of Raiffeisen Bank.

It is supposed to showcase selenium automation skills along presenting page
object pattern and BrowserStack (to be added).

## Test flow
Navigate to Fotolab site, select a product, upload background photo,
add to basket. Stop just before customer login / registration.

## Features
- page-object pattern
- automatic driver management with webdr ivermanager
- cross-browser testing with Chrome, FF, Edge
- test frameworks JUnit, TestNG

## Test flickering
Running cross browser causes the test to fail occasionally.
Here is the rundown of the most common errors.

### Chrome
- uploading photo may fail or generate alert pop-up
