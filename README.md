Written in kotlin and spring boot.  
To run the server with gradle, run:
`gradlew bootRun`.
Or use Intellij Idea.  
The server should start on http://localhost:8080  
It has an index.html page to manually test the conversion.

To run the tests, you should copy test files from here:
https://yadi.sk/d/8OhAPEuwmZxbCA
to the folder `test_video`.

Working curl command:
```
curl \
 -X POST \
 -H "Content-Type: multipart/form-data" \
 -F 'settings={\"format\":\"mp4\",\"video\":{\"codec\":\"mpeg4\",\"bitRate\":1,\"frameRate\":10}}'\
 -F "file=@bunny.mp4" \
 -o curl-converted.mp4 \
 http://localhost:8080/api/convert   
```

Also there is a test in [test_request.rest](test_request.rest)

Full settings json example:
```json
{
  "format" : "mp4",
  "video" : {
    "codec" : "mpeg4",
    "bitRate" : 160000,
    "frameRate" : 10
  },
  "audio" : {
    "codec" : "libmp3lame",
    "bitRate" : 64000,
    "samplingRate" : 22050
  }
}
```

All parameters are optional and use the initial video format by default.

More about parameters meaning you can read on the library github: 
https://github.com/a-schild/jave2/wiki/Examples