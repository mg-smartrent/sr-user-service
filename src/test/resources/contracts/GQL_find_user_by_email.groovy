import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return user by email='sys.admin@smartrent.com'"

    request {
        url "/graphql"
        method POST()
        headers {
            contentType applicationJson()
        }
        body('''
{
  "operationName" : null,
  "query" : "query($email: String! ) { findByEmail( email: $email ) { id status email lastName firstName lastName modifiedDate createdDate dateOfBirth enabled gender } }",
  "variables" : {
    "email" : "sys.admin@smartrent.com"
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
    "findByEmail": {
      "id": "000000000000000000000000",
      "status": "Active",
      "email": "sys.admin@smartrent.com",
      "lastName": "Administrator",
      "firstName": "System",
      "modifiedDate": "2020-07-18T07:55:26.977Z",
      "createdDate": "2020-07-18T07:55:26.977Z",
      "dateOfBirth": "2020-07-18T07:55:26.977Z",
      "enabled": false,
      "gender": "Unknown"
    }
  }
}
''')
    }
}