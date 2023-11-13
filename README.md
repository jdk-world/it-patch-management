# Smart IT Patch Management System

```mermaid
graph TB;
    style AA fill:#F9F9F9,stroke:#DCDCDC,stroke-width:2px;
    style WebPage fill:#F9F9F9,stroke:#DCDCDC,stroke-width:2px;
    subgraph AA[EasyConnect Solution Architecture Diagram]
        Employee[actor Employee]
        ITAdmin[actor ITAdmin]
        DB[database DB]
        WebServer[node WebServer]
        EmailService[cloud EmailService]
        CalendarService[cloud CalendarService]
    end

    Employee -->|access web page| WebServer
    ITAdmin -->|access web page| WebServer
    WebServer -->|read/write data| DB
    WebServer -->|send/receive emails| EmailService
    WebServer -->|create/update events| CalendarService

    subgraph WebPage["Web Page"]
        PS["(Publish Slots)"]
        BS["(Book Slots)"]
        SN["(Send Notifications)"]
        SS["(Search Slots)"]
    end

    WebServer -->|provide UI| PS
    WebServer -->|provide UI| BS
    WebServer -->|provide UI| SN
    WebServer -->|provide UI| SS

    ITAdmin -->|publish availability slots| PS
    Employee -->|book slots as per availability| BS
    WebServer -->|notify both groups with emails and calendar invites| SN
    Employee -->|search slots with filters| SS
```



@startuml
!define RECTANGLE class

RECTANGLE it_patch_management {
  Gateway
  UI
}

RECTANGLE slot_management_service {
  AddSlot
  AddSlots
  DeleteSlot
  DeleteSlots
  SlotBookUI
  FilterService
}

RECTANGLE patch_management_services {
  PatchCatalog
  CreatePatch
  DeletePatch
  TagPatch
  UnTagPatch
  SoftScan
}

RECTANGLE user_management_service {
  EmployeeListing
  AddEmployee
  RemoveEmployee
  AdminListing
  AddAdmin
  RemoveAdmin
  AddRegion
  AddRole
  RoleListing
  RegionListing
}

RECTANGLE compliance_reporting_service {
  ComplianceReportListing
  Dashboards
}

RECTANGLE common_services {
  NotificationEventCalendarService
  DatabaseTabs
  Filter
  Pagination
  Search
  Sorting
}

it_patch_management --> slot_management_service
it_patch_management --> patch_management_services
it_patch_management --> user_management_service
it_patch_management --> compliance_reporting_service
it_patch_management --> common_services

@enduml



## Microservices -    
 -  [it-patch-management](https://github.com/jdk-world/it-patch-management)    
 -  [compliance-reporting-service](https://github.com/jdk-world/compliance-reporting-service)       
 -  [patch-management-services](https://github.com/jdk-world/patch-management-services)       
 -  [user-management-service](https://github.com/jdk-world/user-management-service)   
 -  [slot-management-service](https://github.com/jdk-world/slot-management-service)    



