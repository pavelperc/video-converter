Written in kotlin and spring boot.  
To run the server with gradle, run:
`gradlew bootRun`.
Or use Intellij Idea.  
The server should start on http://localhost:8080

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