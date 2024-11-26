# Function and APIs


## overview

>as you can see below, this project is a demo so these names are not serious.  I think it's gonna hard for common tools to scan over the catalog.

1. common user: `x://firstclass/outtime/login`
2. administer user: `x://firstclass/ofF_line/dean-on-line`




### Common user

<procedure title="Common user page" id="out-time">
    <step>
        <p>When you enter this URL you are gonna see:</p>
        <img src="o-t-login.png" alt="common user login page" border-effect="line"/>
    </step>
    <step>
        <p>If you are not a registered user, click Register</p>
        <img src="register.png" alt="common user register page" border-effect="line"/>
    </step>
    <step>
        <p>As you can see clearly, login needs  username and password, register needs username, password, email and avatar.</p>
    </step>
</procedure>


### Administer Dean

<procedure title="Administer Dean page" id="ofF_line">
    <step>
        <p>Here's Administer login page</p>
        <img src="dean-login.png" alt="administer Dean login page" border-effect="line"/>
    </step>
</procedure>


## Details

So if you are a common user, you will see these after you login:

>home page

![home page](home.png){ width=650 }{border-effect=line}

- `Make It Public`: A check box to check if you want to post a public post.
- Other things are normal, enter your content and choose file you want to upload, then click <shortcut>Post</shortcut>.



### Overview of All Pages


<tabs>
    <tab title="Off-Line">
        <p>Home page</p>
    </tab>
    <tab title="Public Posts">
        <p>public space for displaying all public posts</p>
    </tab>
    <tab title="My Profile">
        <p>This is your profile page, you can see your posts but you can't edit anything(because I didn't develop this.)</p>
    </tab>
    <tab title="Search Posts">
        <p>This is a search page, you can search posts by title or content</p>
    </tab>
</tabs>



## Public Posts



This is a public space that display all public posts.


![public page](public-posts.png){ width=550 }



## My Profile


![profile](profile.png)

When i made this screenshot I login as a common user `e`, you can see from picture.

This post of user `e` is private so that you can't see it in public posts space but in profile page. And it has a attachment which you can download.


## Search Posts

![search posts page](search.png)

It uses "LIKE" to search, which is a SQL query format.

These posts match the search keywords will be displayed below.


## Dean Pages


![dashboard](re-connect.png)

WHen login as Dean, Dean can see all users and posts.


![dean's power](dean-detials.png)

this is a detail page about how dean administer the users and posts.
