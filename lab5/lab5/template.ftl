<html>
<head>
  <title>${catalog.name}</title>
</head>
<body>
  <h1>${catalog.name}</h1>

  <ol>
    <#list catalog.items as item>
    <li>
      <ul>
        <li>id: ${item.id}</li>
        <li>title: ${item.title}</li>
        <li>location: ${item.location}</li>
        <#list item.tags?keys as key>
          <li> ${key}: ${item.tags[key]}</li>
        </#list>
      </ul>
    </li>
    </#list>
  </ol>

</body>
</html>