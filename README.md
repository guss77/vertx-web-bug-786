# Minimal test case to reproduce trailing slash discrimination bug with pattern routes

See [Vert.x Web issue #786](https://github.com/vert-x3/vertx-web/issues/786) for more details.

This project contains a simple Vert.X Web application and a unit tests to exercise some routes in the application.

## Application setup

The application is a simple library demo and provides the following routes:

 * `/books` - list all books in the library. Should also work with `/books/`
 * `/books/:id` - retrieve information on a single book by its index in the book list (0-based)
 * `/books/:id/authors` - retrieve information on authors of a single book, but the book's index in the book list. Should also work with '/books/:id/authors/`.

## Test status

[![CircleCI](https://circleci.com/gh/guss77/vertx-web-bug-786.svg?style=svg)](https://circleci.com/gh/guss77/vertx-web-bug-786)
