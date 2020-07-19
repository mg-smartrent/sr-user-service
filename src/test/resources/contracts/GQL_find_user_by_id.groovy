import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return user by id=000000000000000000000000"

    request {
        url "/graphql"
        method POST()
        headers {
            contentType applicationJson()
        }
        body('''
{
  "operationName" : null,
  "query" : "query($id: String! ) { findById( id: $id ) { id status email lastName firstName lastName modifiedDate createdDate dateOfBirth enabled gender } }",
  "variables" : {
    "id" : "000000000000000000000000"
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
    "findById": {
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