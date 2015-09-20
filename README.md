# Android Projects
## MyAppPortfolio

An application that displays a screen with some buttons, actually nothing interesting.

## PopularMovies

It's application that retrieves a list of movies based on a selected criteria using [themoviedb](https://www.themoviedb.org/) api, and the content is handled by a GridView with [Picasso](http://square.github.io/picasso/) 

The current version has only two criteria, Average Rate and Popularity, you can switch between them from the configuration menu.
If you want to compile and use it, you must set your own Api key in the file FechDataTask.java. In this file there is a static constant named KEY where the value should be entered, if don't have one you can request it  from [themoviedb](https://www.themoviedb.org/)
