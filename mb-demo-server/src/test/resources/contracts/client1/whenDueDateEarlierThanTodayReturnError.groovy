import org.springframework.cloud.contract.spec.Contract

 Contract.make {
     description "should return 400 error due date is earlier than today"
     request {
         method POST()
         url("/todos") {
             body("{\"description\": \"test desc\", \"dueDate\":  \"2012-04-23T18:25:43.511Z\"}")
             headers {
                 contentType('application/json')
             }
         }
     }
     response {
         status 400
     }
 }