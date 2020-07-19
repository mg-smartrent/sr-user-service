import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return user by email=sys.admin@smartrent.com"

    request {
        url "/rest/users?email=sys.admin@smartrent.com"
        method GET()
        headers {
            contentType applicationJson()
        }
    }

    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body('''
{
      "id": "000000000000000000000000",
      "status": "Active",
      "email": "sys.admin@smartrent.com",
      "lastName": "Administrator",
      "firstName": "System",
      "modifiedDate": "1595058926977",
      "createdDate": "1595058926977",
      "dateOfBirth": "1595058926977",
      "enabled": false,
      "gender": "Unknown"
}
''')
    }
}