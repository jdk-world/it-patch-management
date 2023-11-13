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



```mermaid
graph TD;
  subgraph it_patch_management[IT Patch Management System]
    gateway(Gateway)
    compliance_reporting(Compliance Reporting Service)
    patch_management(Patch Management Services)
    user_management(User Management Service)
    slot_management(Slot Management Service)
  end

  gateway -->|Gateway| compliance_reporting
  gateway -->|Gateway| patch_management
  gateway -->|Gateway| user_management
  gateway -->|Gateway| slot_management

  subgraph features
    add_slot(Add Slot)
    add_slots(Add Slots)
    delete_slot(Delete Slot)
    delete_slots(Delete Slots)
    slot_book_ui(Slot Book UI)
    filter_service(Filter Service)
  end

  add_slot -->|slot_management| slot_management
  add_slots -->|slot_management| slot_management
  delete_slot -->|slot_management| slot_management
  delete_slots -->|slot_management| slot_management
  slot_book_ui -->|slot_management| slot_management
  filter_service -->|slot_management| slot_management
  slot_management -->|slot_management| slot_management_service
  compliance_reporting -->|compliance_reporting| compliance_reporting_service
  patch_management -->|patch_management| patch_management_services
  user_management -->|user_management| user_management_service

```



## Microservices -    
 -  [it-patch-management](https://github.com/jdk-world/it-patch-management)    
 -  [compliance-reporting-service](https://github.com/jdk-world/compliance-reporting-service)       
 -  [patch-management-services](https://github.com/jdk-world/patch-management-services)       
 -  [user-management-service](https://github.com/jdk-world/user-management-service)   
 -  [slot-management-service](https://github.com/jdk-world/slot-management-service)    



