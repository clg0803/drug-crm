# drug-crm
A very simple frontend-backend  detached application built on springboot and vue.js for practice purpose

# structure
```shell
 - drug-orm
  -- spring boot application 
 - drug-ui
  -- vue application
```

# how to use
  1. download it and open tow seperate project in IDE(such as Intellj IDEA)
  2. import .sql file to local daatabase, `MySQL` supported so far
  3. enable `SMTP` service in your own e-mail and configure `username` and `password` in `drug-orm(springboot) / application.yaml` and `util/EmailService.java line: 16`
  4. use command `npm serve run` to run frontend application, click to run backend application in IDE
