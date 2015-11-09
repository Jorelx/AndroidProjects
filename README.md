# Android Projects
## MyAppPortfolio

An application that displays a screen with some buttons, actually nothing interesting.

## PopularMovies

It's application that retrieves a list of movies based on a selected criteria using [themoviedb](https://www.themoviedb.org/) api, and the content is handled by a GridView with [Picasso](http://square.github.io/picasso/). This is 2nd stage of this project, so many stuff was added such as offline mode for favorite movies, tablet support, new secction for videos and reviews. 

The current version has only three sort criteria, Average Rate, Popularity and Favorite, you can switch between them from the configuration menu.
If you want to compile and use it, you must set your own Api key in the file App gradle configuration. In this file there is a buildConfigField named MOVIEDB_API_KEY where the value should be entered, if don't have one you can request it from [themoviedb](https://www.themoviedb.org/)
