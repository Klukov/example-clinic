## 1. MUST HAVE

1. As **Public user** I want to query all available doctors with concrete specialization in specific time. <br />
   **STATUS:** specialization have to be added. <br />
   **EXAMPLE:** http://localhost:8080/public/v1/visit/doctors?from=2019-05-01T23:10:01&to=2023-05-01T23:10:01
2. As **Public user** I want to query all available time slots for doctorId <br />
   **STATUS**: DONE <br />
   **EXAMPLE:** http://localhost:8080/public/v1/visit/available?from=2019-05-01T23:10:01&to=2023-05-01T23:10:01&doctor=1
3. As **Public user** I want to book time slot (visit). I have to write my name, lastName, pesel and remarks. <br />
   **STATUS:** add input validation, add phone and email <br />
   **EXAMPLE:** todo <br />
4. As **Doctor** I want to see all my visits (in all statuses) in time specified by dates <br />
   **STATUS:** tests are needed <br />
   **EXAMPLE:** todo <br />
5. As **Doctor** I want to finalize visit, put final price and remarks <br />
   **STATUS:** todo <br />
   **EXAMPLE:** todo <br />
6. As **Receptionist** I want to find all visits in clinic and filter them by status <br />
   **STATUS:** todo <br />
   **EXAMPLE:** todo <br />
7. As **Receptionist** I want to book a visit, because patient is calling a clinic <br />
   **STATUS:** todo <br />
   **EXAMPLE:** todo <br />
8. As **Receptionist** I want to confirm visit for doctor, by calling patient and confirming with him the visit <br />
   **STATUS:** todo <br />
   **EXAMPLE:** todo <br />
9. As **Receptionist** I want to cancel visit for doctor, because called patient or doctor cannot be on the visit <br />
   **STATUS:** todo <br />
   **EXAMPLE:** todo <br />

## 2. GOOD TO HAVE:

1. As **Public user** I want to get confirmation email/sms for my visit <br />
   **STATUS:** todo <br />
2. System need to protect itself from harmful users by checking phone number and ip <br />
   **STATUS:** todo <br />
3. Deduplication of patients by pesel number <br />
   **STATUS:** todo <br />

## 3. NICE TO HAVE

1. Patient panel in our system <br />
2. 
