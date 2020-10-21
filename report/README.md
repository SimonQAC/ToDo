
# ToDo JMeter Tests

JMeter Tests for my ToDo List rest API


## Notes

**UPDATE:** ToDo List.rar contains old versions of the jmx files

So I had some real trouble getting my tests to function well with logic controllers.
Within `ToDo List.rar` you'll find my attempts at creating my tests without any controllers.
I think I also accidentally overwrote a good soak test I created at one point.

The main errors produced by my tests I think are to do with my backend, which hasn't appreciated this level of testing at all.
My backend lacks the function to delete a task, and doesn't give a good response when deleteing a user. 
These were difficult to work around, and one revision of my tests attempted to assert a response from deletion that didn't exist.

My backend also somehow forgets what mappings it has when its undergoing a heavy load, 
some stress tests result in pure failures because it loses the ability to recognise which mappings are available.
