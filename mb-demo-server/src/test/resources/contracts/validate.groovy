import org.springframework.cloud.contract.spec.Contract
[
Contract.make {
    description "should return bad request when new todo but status already completed"
    request {
        method POST()
        url("/todos") 
        body('''
            { 
                "description": "test desc2",
                "status": "COMPLETED"
            }
        ''')
        headers {
            contentType('application/json')
        }
    
    }
    response {
        status BAD_REQUEST()
    }
},
Contract.make {
    description "should return 201 when data is valid"
    request {
        method POST()
        url("/todos") 
        body('''
            { 
                "description": "test desc"
            }
        ''')
        headers {
            contentType('application/json')
        }
        
    }
    response {   
        status 201
    }
}
]