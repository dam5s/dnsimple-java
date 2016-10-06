
A few points:

 * The app would deserve in general a bit more structure:
    - Regrouping classes by domain concepts.
    - Technically parts of the API should be extractable to components if necessary
      (when the domain grows that would be nice)
    - The problem is that there is a cyclic dependency between the Client and all the resource types (Contacts, Zones...)

 * It's hard to find the entry point classes
   because their name is only the pluralized version of the types they give access to.
   Domains could potentially become something like "DomainsApi".

 * I have done a few commits before adding this file to highlight some other problems I've found.

 * Java indentation convention is 4 spaces. Or at least it's the most used:
   https://google.github.io/styleguide/javaguide.html

 * The code seems to not be compatible with Java 6 even though the pom.xml mentions source compatibility 1.6.
   (See usage of ReflectiveOperationException class in Client)

 * Might as well migrate the build from Maven to Gradle,
   it's become the standard and is much more flexible than Maven.

 * It seems the Client class' code is not fully covered by tests
   I could find a couple mistakes in it while reading it (see previous commits for fixes).
   Might as well delete the methods that are unnecessary, or the arguments that are unused.
