import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return 201 when due date is null"
    request {
        method POST()
        url("/todos") {
            body("{\"description\": \"test desc\"}")
            headers {
                contentType('application/json')
            }
        }
    }
    response {   
        status 201
    }
}