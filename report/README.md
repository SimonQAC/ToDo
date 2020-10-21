
# ToDo JMeter Tests

JMeter Tests for my ToDo List rest API


## Notes

So I had some real trouble getting my tests to function well with logic controllers.
Within `ToDo List.rar` you'll find my attempts at creating my tests with these.
I think I also accidentally overwrote a good soak test I created at one point.

The main errors produced by my tests I think are to do with my backend, which hasn't appreciated this level of testing at all.
My backend lacks the function to delete a task, and doesn't give a good response when deleteing a user. 
These were difficult to work around, and one revision of my tests attempted to assert a response from deletion.

My backend also somehow forgets what mappings it has when its undergoing a heavy load, 
some stress tests result in pure failures because it loses the ability to recognise which mappings are available.

I'm going to attempt to work with the `.rar` version of the tests to get them fully working, but, failing that, a sub-standard jmx will still be present.

Also my reports probably aren't up to date, they exist, but aren't representative of the current tests, 
my focus is mainly on fixing the tests rather than producing and updating the reports.
