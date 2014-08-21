# Brainiac

Brainiac is a company dashboard, optimized for a display on large monitors - so really more of a billboard. It's aimed at software development organizations, but can definitely be used more generally. 

It can display information from a variety of sources, including: Jenkins, Nagios, Pager Duty, Github, Google Weather, Twitter, Chicago Transit Authority, Elovation, Jukebox2 and RSS feeds.
<img src="https://raw.github.com/brighttag/brainiac/master/resources/public/images/example_screen_1.png" />

Brainiac is a Leiningen app, written in Clojure. It is currently optimized for display on an HDTV (1920x1080 resolution), on recent Webkit and Firefox browsers.

## Usage

First, ensure that you have Java installed on your web server. This app uses Clojure, which runs on the Java VM.

```
sudo apt-get update
sudo apt-get install default-jre
```

Now, clone this repo, make the `lein` script executable, and bring down all the dependencies.

```
git clone https://github.com/BrightTag/brainiac.git
cd brainiac
chmod a+x lein
./lein deps
```

Now copy `config.yml.example` to `config.yml` and edit the final to configure the panels.

Finally, it's time to run it! By default it runs on port 8080, but if you'd like a different port just specify it using the `-p` flag.

```
./lein run -p 18080
```

You probably want to wrap this in a service so it runs even when the server reboots.

## License

Distributed under the Eclipse Public License, the same as Clojure.

CSIRO Radio Observatory image by Flickr user amandabhslater, used under Creative Commons BY-SA
