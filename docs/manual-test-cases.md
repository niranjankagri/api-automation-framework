# Manual test cases

The API test cases this framework automates, written as a tester would run them by hand (curl,
Postman or a browser). Every case has an ID; the TestNG method that covers it starts with the same ID
(`tc201_validateGetAllUsersEndpoint`) and so does its description (`[TC-201] ...`), so a failure in
the report leads straight to the case below.

Not listed: `passTest`, `failTest`, `skipTest` (group `demo`), which pass, fail and skip on purpose
to show the report layouts.

## Setup

- Base URL: <https://jsonplaceholder.typicode.com> (a public fake API).
- Test user: username `Samantha` (`src/resources/data.properties`).
- JSONPlaceholder fakes write operations: POST, PUT, PATCH and DELETE answer realistically, but nothing
  is stored. A read after a write therefore shows the old data; that is expected.
- Valid e-mail (TC-207): `^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,6}$`, case-insensitive, e.g. `name@example.com`.

## Summary

| ID | Title | Priority | Automated by |
|---|---|---|---|
| TC-201 | Get all users | High | `tc201_validateGetAllUsersEndpoint` |
| TC-202 | Get a user by username, every field filled | High | `tc202_validateGetUserByUsernameEndpoint` |
| TC-203 | Get all posts | Medium | `tc203_validateGetAllPostsEndpoint` |
| TC-204 | Get the posts of a user by user id | High | `tc204_validateGetAllPostsOfUserByUserIdEndpoint` |
| TC-205 | Get all comments | Medium | `tc205_validateGetAllCommentsEndpoint` |
| TC-206 | Get the comments on a user's posts by post id | High | `tc206_validateGetAllCommentsOnPostByPostIdEndpoint` |
| TC-207 | Every e-mail in the comments on the user's posts is valid (main scenario) | High | `tc207_validateEmailFormatOnComments` |
| TC-208 | Create a post | Medium | `tc208_validateCreatePostEndpoint` |
| TC-209 | Replace a post with PUT | Medium | `tc209_validateUpdatePostbyPutMethod` |
| TC-210 | Change a post with PATCH | Medium | `tc210_validateUpdatePostbyPatchMethod` |
| TC-211 | Delete a post | Low | `tc211_validateDeletePostEndpoint` |

All in `src/test/java/com/typicode/tests/TypicodeTest.java`.

---

### TC-201 Get all users

| # | Step | Expected result |
|---|---|---|
| 1 | `GET /users` | `200`; a non-empty list |

### TC-202 Get a user by username, every field filled

| # | Step | Expected result |
|---|---|---|
| 1 | `GET /users?username=Samantha` | `200`; one user with `username` `Samantha` |
| 2 | Check the user | `id`, `name`, `email`, `address`, `phone`, `website`, `company` are all present |

### TC-203 Get all posts

| # | Step | Expected result |
|---|---|---|
| 1 | `GET /posts` | `200`; a non-empty list |

### TC-204 Get the posts of a user by user id

| # | Step | Expected result |
|---|---|---|
| 1 | Find Samantha's `id` (TC-202) | e.g. `3` |
| 2 | `GET /posts?userId=<id>` | `200`; at least one post |

### TC-205 Get all comments

| # | Step | Expected result |
|---|---|---|
| 1 | `GET /comments` | `200`; a non-empty list |

### TC-206 Get the comments on a user's posts by post id

| # | Step | Expected result |
|---|---|---|
| 1 | Get Samantha's posts (TC-204) | A list of post ids |
| 2 | For each post `GET /comments?postId=<post id>` | `200` each; at least one comment in total |

### TC-207 Every e-mail in the comments on the user's posts is valid

| # | Step | Expected result |
|---|---|---|
| 1 | Get all comments on Samantha's posts (TC-206) | A list of comments |
| 2 | Check every comment's `email` against the pattern above | All valid. Note **every** invalid address with its comment id before failing the case |

### TC-208 Create a post

| # | Step | Expected result |
|---|---|---|
| 1 | `POST /posts` with `{"userId":<Samantha's id>,"title":"<random word>","body":"<random word>"}` | `201`; the answer has an `id` |

### TC-209 Replace a post with PUT

| # | Step | Expected result |
|---|---|---|
| 1 | `GET /posts/1`, change title and body to random words | |
| 2 | `PUT /posts/1` with the whole post | `200`; `id` `1` |

Known quirk of the automated test: it sends the Java object's default text instead of JSON
(`Post` has no `toString()`); JSONPlaceholder still answers 200. Send real JSON when testing by hand.

### TC-210 Change a post with PATCH

| # | Step | Expected result |
|---|---|---|
| 1 | `PATCH /posts/1` with `{"title":"<random>","body":"<random>"}` | `200`; `id` `1` |

### TC-211 Delete a post

| # | Step | Expected result |
|---|---|---|
| 1 | `DELETE /posts/1` | `200` |
