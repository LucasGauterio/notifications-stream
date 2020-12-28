# notifications

To run the application execute the following the commands:
```
sh prepare_env.sh
```
*this command will run consul, zookeeper, kafka, kafkadrop UI(localhost:9000)

```
sh build.sh
```
*this command will create the .jar and imagens for each service

```
sh run.sh
```
*this command will run all services

```
sh build_run.sh
```
*this command will create the .jar and imagens for each service and also run the services

https://drive.google.com/file/d/1QpPX7kikCLnQvIWfYYSgrxJ6huG_cxr8/view?usp=sharing

Suggested Stack:
Java Spring Boot
Apache Kafka
MongoDB

Suggested Stack:
Java Spring Boot
Apache Kafka
MongoDB

### corp-notifications-api
- notifications interface to the client
- defines the basic notification request structure (client, user, intent[CONFIRMATION, CANCELLATION, PAYMENT, VOUCHER], data, context, tags)
- defines the notifications response (notificationId)
- defines the callback request sent to the client (notificationId, Map<CHANNEL,STATUS>)
- consumes notifications topic status success to send the callback request to the client  based on the preferences
- consumes notifications topic status failure to apply fallback strategies based on the preferences
- produces a topic message to the notifications with header status = initiated

### corp-notifications-audit
- consumes notifications topic all
- has its own database
- defines rules to what is persisted
- provides an API to the audit front-end

### corp-notifications-preferences
- consumes notifications topic (status=initiated)
- based on the notification params finds the client and user preferences
- client preferences includes client data, user data, templateId and data variables for templates, and available/preferred channels
- gets user data from profile service
- user preferences includes available/preferred channels
- add a preferences structure to the message that is used by templating service and channels
- produces messages to notifications topic (status=preferred)
- has its own database
- provides an API to the configuration front-end

### corp-notifications-templates
- consumes topic notifications (status=preferred)
- generates the content for all notifications based on the preferences
- produces messages to notifications topic (status=templated)
- has its own database
- provides an API to the templating front-end

### corp-notifications-email
- consumes topic notifications (status=templated)
- sends e-mails through a provider
- produces channel status messages to notifications topic (status=sent)

### corp-notifications-webpush
- consumes topic notifications (status=templated)
- sends web push messages through a provider
- produces channel status messages to notifications topic (status=sent)

### corp-notifications-sms
- consumes topic notifications (status=templated)
- sends SMS messages through a provider
- produces channel status messages to notifications topic (status=sent)

### corp-notifications-whatsapp
- consumes topic notifications (status=templated)
- sends WhatsApp messages through a provider
- produces channel status messages to notifications topic (status=sent)

### corp-notifications-mobilepush
- consumes topic notifications (status=templated)
- sends mobile push messages through a provider
- produces channel status messages to notifications topic (status=sent)

