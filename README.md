# EducationZEN Application

The eduZEN-Application is realised as a plugin for the DeepaMehta <https://github.com/jri/deepamehta> software platform.

It`s aim is to support the reformation and efficiency of scientific tutorials held in higher education through delivering useful day-to-day functionality supporting _learning_ and _teaching_ (with the utmost respect to) any subject of interest and (with the upmost ruggedness to) any given learner.

Project Website: <http://web.eduzen.tu-berlin.de>

# Installation & Usage

This bundle ships and installs the eduZEN-Application model with it's corresponding restful webservice implementation.

To be fully-featured, additionally install our LaTeX-Renderer (from repo:mukil/dm4-mathjax-renderer) and the prototypical customized user interface (from repo:mukil/eduzen-views).

For usage hints and testing purposes of the customi user interface for the eduZEN-Application, see repo:eduzen-views.

# Changelog

1.0-SNAPSHOT, Feb 18, 2013:
- based on the latest deepamehta-4.0.14 release

0.3.0-SNAPSHOT, January, 2013

0.2.0-SNAPSHOT, Oct 05, 2012:

- moved mathjax integration to it`s own plugin <https://github.com/mukil/dm4-mathjax-renderer>
- adapted the model towards the "approaching/excercises/and/comments"-launch
- runs with DeepaMehta 4.0.13-SNAPSHOT
- and updated this readme cause of a slightly to fast git commit command (see last commit)

0.1.0-SNAPSHOT, Jul 20, 2012:

- TeX based info_renderer <http://www.mathjax.org> introduced to  'dm4.webclient'
- eduZEN-Application extended about '/eduzen/content/create'-service and application 'content' model
- eduZEN-Application Model (as of revision 0.3) available for experimental testing with dm4-webclient

Last edited on Sep 25, 2012

