# Unverified behaviors

## Question

1. When loading stopwords, should the case where there's no stopwords be handled? In that case, all valid words are keywords?

2. When using `loadDocs()` method in `Engine` class, how to handle empty string parameter `e.loadDocs("")`?

3. Calling `loadDocs()` multiple times add more docs or reset the list of docs?

4. Does `loadDocs()` have to filter out all but `.txt` files?

5. Can the target folder of `loadDocs` contain nested folders?

## Answer

1. When stopwords is empty, all valid words are keywords.

2. When calling `loadDocs("")`, return 0.

3. Calling `loadDocs()` multiple times add more docs.

4. Only need text file, so maybe just `.txt`

5. No, so no need to handle such cases.
