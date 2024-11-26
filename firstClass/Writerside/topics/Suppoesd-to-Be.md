# Suppoesd to Be


>ok, first i need to talk about this briefly.
> A demo website project for testing.

## vulnerable kits

1. xss
2. virus download file
3. sql injection & bypass


## now

1. I finished XSS, based on `Thymeleaf` template engine. And I haven't made a filter for it yet.
2. I want to make a virus download file by python `stealer`, which i will reduce the degree of danger.
3. sql injection seems to be unpossible, because I use `spring-data-jdbc` to connect to database. `Query`  makes it safer. but i think, maybe? As I mentioned, I haven't make any filter for this project.
4. and more, there's a lot of problems like, 
   - administer user can log in website as common user
   - URL with Mapping problem
   - search match  rule is not good, it can even search for private post--but this i can make it a trap, like, virus file.
   - tbc
5. I got a new thought when I was reading these vulnerable code of Java Web, that catalog. it may connect to  xss.

## log for this project

**if you think you know something, just try to build it and  you will find the answer is, totally no.**
