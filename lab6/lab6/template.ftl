<html>
<head>
  <title>Movie List</title>
</head>
<body>
  <h1>Movies by Score, descending</h1>

  <ol>
    <#list movies as movie>
    <li>
        <p>${movie.toString()}</p>
    </li>
    </#list>
  </ol>

</body>
</html>