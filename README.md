# Minimal test case to reproduce trailing slash discrimination bug with pattern routes

See [Vert.x Web issue #786](https://github.com/vert-x3/vertx-web/issues/786) for details about this failure, though this issue [is already fixed in current Vert.x distributions, as of October 2021](https://github.com/vert-x3/vertx-web/pull/1960)

This project contains a simple Vert.X Web application and a unit tests to exercise some routes in the application.

## Application setup

The application is a simple library demo and provides the following routes:

 * `/books` - list all books in the library. Should also work with `/books/`
 * `/books/:id` - retrieve information on a single book by its index in the book list (0-based)
 * `/books/:id/authors` - retrieve information on authors of a single book, by the book's index
     in the book list. Should also work with `/books/:id/authors/`.

## Test status

[![Github Action](https://github.com/guss77/vertx-web-bug-786/actions/workflows/maven.yml/badge.svg)](https://github.com/guss77/vertx-web-bug-786/actions/workflows/maven.yml)
