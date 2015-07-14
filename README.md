# Slackard
A Bot for [Slack](https://slack.com/) written in [Clojure](http://clojure.org).

## Usage

I highly recommend using [Leiningen](http://leiningen.org/). 

Of course you can use the run command as shown below.

```sh
$ cd <project folder>
$ lein run
```

But I'd recommend for using Leiningen's uberjar command to compile to a jar file; optionally using something like a [screen session](http://aperiodic.net/screen/start) too.

```sh
$ screen
$ cd <project folder>
$ lein uberjar
$ java -jar ./target/anyviz-0.1.0-SNAPSHOT-standalone.jar
$ <ctl+a d>
```

## License
Copyright © 2015 [Bob Williams](https://github.com/bobwilliams), distributed under the [MIT License](LICENSE.md)