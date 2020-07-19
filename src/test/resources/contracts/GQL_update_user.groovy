import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should update user password and return the user (id=000000000000000000000000)"

    request {
        url "/graphql"
        method POST()
        headers {
            contentType applicationJson()
        }
        body('''
{
  "operationName" : null,
  "query" : "mutation($user: UserInput!) { update( user: $user) { id status email lastName firstName lastName modifiedDate createdDate dateOfBirth enabled gender } }",
  "variables" : {
    "user": {
      "id": "000000000000000000000000",
      "status": "Active",
      "email": "sys.admin@smartrent.com",
      "lastName": "Administrator",
      "firstName": "System",
      "modifiedDate": "2020-07-18T07:55:26.977Z",
      "createdDate": "2020-07-18T07:55:26.977Z",
      "dateOfBirth": "2020-07-18T07:55:26.977Z",
      "enabled": true,
      "gender": "Unknown",
      "password": "newpassword"
    }
  }
}
''')
    }

    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body('''
{
  "data": {
    "update": {
      "id": "000000000000000000000000",
      "status": "Active",
      "email": "sys.admin@smartrent.com",
      "lastName": "Administrator",
      "firstName": "System",
      "modifiedDate": "2020-07-18T07:55:26.977Z",
      "createdDate": "2020-07-18T07:55:26.977Z",
      "dateOfBirth": "2020-07-18T07:55:26.977Z",
      "enabled": true,
      "gender": "Unknown"
    }
  }
}
''')
    }
}