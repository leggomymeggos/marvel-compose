# Marvel Compose

This project is to learn about using Compose and Mavericks. In order to make it
more "real," the data is provided by the [Marvel API](https://developer.marvel.com/docs).

In order to use the app, you will need to create a `.secrets.yml` file in the root directory
with the following format: 

```
---
MARVEL_API:
    PUBLICKEY: <your Marvel API publickey>
    PRIVATEKEY: <your Marvel API privatekey>
```

`.secrets.yml` will be ignored by git and the variables will be automatically added to
your `BuildConfig`. 