# Random Duck

App that allows you to get and share random duck from https://random-d.uk/api

## Pictures

<p>
  <img src="ReadmeRes/Picture1.jpg" width="200" alt="App Picture 1">
  <img src="ReadmeRes/Picture2.jpg" width="200" alt="App Picture 2">
  <img src="ReadmeRes/Picture3.jpg" width="200" alt="App Picture 3">
  <img src="ReadmeRes/Picture4.jpg" width="200" alt="App Picture 3">
</p>

## Stack

- Kotlin
- Jetpack Compose
- Retrofit
- Dagger Hilt

## Functionality

- Horizontal Swipe - swiping left brings up the next random duck, while swiping right returns to the previous duck.
- Vertical Swipe - swiping up shares the URL of the picture, and swiping down downloads the picture.
- Actions - all actions are executed once the drag motion stops, with the card automatically animating back to its starting position.
- Info Screen - an info screen provides links to the source code, API, tips, and review integration.
- Background Cards - as users swipe, a background image becomes visible, visually indicating the action in that direction.
- Error Handling - error screens are displayed when something goes wrong.
- Image Preloading - next images are preloaded to reduce user wait times.
- Start Animation - upon app launch, a brief drag animation introduces users to the interaction method.
