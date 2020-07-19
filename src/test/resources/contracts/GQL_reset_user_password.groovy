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
  "query" : "mutation($id: String!, $rawPassword: String!) { resetPassword( id: $id, rawPassword: $rawPassword ) { id status email lastName firstName lastName modifiedDate createdDate dateOfBirth enabled gender } }",
  "variables" : {
    "id" :          "000000000000000000000000",
    "rawPassword" : "12341234"
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
    "resetPassword": {
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