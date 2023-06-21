Allm's challenge application

Simple hospitals appointment system

Remarks:
- Overall projects structure is split to follow hexagonal architecture and dependency inversion:
    - There are no any dependency on implementation between logical layers, which looses coupling of layers
    - net.allm.challenge.controller.api package should be moved to a separate artifact 
  and shared with other teams (frontend, teams which have integrations with us, partners)
    - Internal structure can be also split to separate modules - 
      - net.allm.challenge.service.api - core/domain module. All  others can have a dependency on it
      - net.allm.challenge.service.impl - service implementation
      - net.allm.challenge.repository.api - interface of repository. Service implementation has a dependency on it
      - net.allm.challenge.repository.impl - implementation of repositories, depends on its api and core
      - net.allm.challenge.controller.impl - implementation of controllers, depends on its api and core
    Having above described structure means that no dependency on implementation module accepted, except for root assembling module
- Division between service and repository layer is a bit controversial and a matter of discussion. Though generally I
tried to write service layer, so it look independent of particular database storage/framework choice etc.
- Service layer looks too simple in this case. Generally I left space for, i.e., integrations with hospitals etc.
- Also, currently there is no specific errors processing. If it is error in logic (i.e., exceeded limit of 
upcoming visits for patient) - it should be located in the service-impl layer
- I have added dateTime field to appointment database, though in real life there should be time slots, 
based on doctors schedule, etc. This was a bit beyond requirements, so I did just with dateTime
- Basic http authentication applied. All users have password "password"
- There is no physical relationship between patients and users. Patients table contains only username field. 
This is done on purpose, security-related tables should be moved to a separate schema with separate permissions.

- Still more tests can be added, but I covered the main workflow. Check VisitControllerIntegrationTest.
- Instead of deleting visits we can just mark them as deleted. Can be useful just in case... Or maybe just adding audit collection is enough
