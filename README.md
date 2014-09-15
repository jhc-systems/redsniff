redsniff
========

A fluent, feedback-rich API for WebDriver or other similar libraries

redsniff is a thin higher-level API on top of [Selenium Webdriver](http://code.google.com/p/selenium/wiki/GettingStarted) that integrates with [hamcrest](http://code.google.com/p/hamcrest/wiki/Tutorial) to enable writing expressive, effective automated tests for interacting with, and making assertions against websites. 

(It is originally based on the experimental [Selenium Lift-Style API](http://code.google.com/p/selenium/wiki/LiftStyleApi) but ended up diverging substantially.)

###Fluent Interface
The style used is as a [Fluent interface](http://en.wikipedia.org/wiki/Fluent_interface), or [Internal DSL](http://martinfowler.com/books/dsl.html).

This means it uses certain tricks to make the API look very readable, `albeit.with(aLotOf(brackets()))`, almost like an English sentence if you took the punctuation out. 

It tends to encourage a *declarative* rather than *procedural* way of thinking about things - *what*, not *how*.

###Quick example

we create a RedsniffWebDriverTester as below  

    RedsniffWebDriverTester browser = new RedsniffWebDriverTester( new ChromeDriver() );
    browser.goTo("http://my-url.com");


Then you can write assertions like this:

    browser.assertPresenceOf( dropDown().that( hasName(a_drop-down) ) );


which means, "**browser**, please confirm the presence of a drop down item that has `name` `'a_drop-down'`"

i.e. *on the browser, check that a dropdown whose 'name' attribute is "a_drop-down" is present somewhere on the page*


###More Examples

Here are some more - the syntax is designed so that what it does is obvious without explanation or comments :

    browser.goTo("http://www.wikipedia.org");
    browser.type("clowns", ''into''( ''textBox()''.that(''hasName''("search"))));
    browser.clickOn(''button''("OK"));

###Feedback - a first class citizen
redsniff puts a very high value on feedback - diagnostic messages that appear when assertions and expectations fail.
Some messages you could get from above examples are:

    Expected: a(n) 3rd drop-down that has name "a_drop-down" to match is <enabled>
    but: only found <2> such elements


    Expected: 3rd drop-down that has name "a_drop-down" to match  is <enabled>
    but:
    element was <disabled>


    Expected: a unique textbox that has name "search" to type "clowns" into, 
    but:
    Did not find any textbox that: {has name "search"}
    <input> (name:Search)  - not matched because name  was:
      '[S]earch'
    instead of:
      '[s]earch'

By contrast, using WebDriver directly would have resulted in just `ElementNotFoundException` in most cases, and it may take you some time to realise you had misspelled the element name.
