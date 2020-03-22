# Event Audio SDK

## Purpose
Define basic data types for submission to / reading from a queue,
for interoprable applications to submit audio-play events, and to play them.

## Event model
This SDK defines a `PlayEvent`, as having a `uuid`, a `filePath`, and a `duration`.
An application could subclass this to add to these properties, and not all are strictly required
(i.e. they are nullable),
but this is the "recommended" shape of audio-play messages.

`uuid` is useful for logging / watching an event go through, application to queue to application.
`filePath` is a path (may be relative) to a file to play. 
`duration` indicates the max length of how long to play the file. This field is a string so
it can be 

## Example application
One use case would be a generic audio-play application.
Listen for audio-play events on a queue, and play the audio file indicated.

## Data sharing
This event-based architecture assumes that the "player" will have access
to files requested to be played. To make that happen, you have a few options.
* Limit requests to just a set of files available on the player.
  * Good for alarm clock or soundboard like systems.
  * Manually manage which audio files you have available to the player.
* Add some sort of audio-directory service, likely with local caching.
  * If the player doesn't have a file locally, reach out to the directory service to find it.
  * Then cache it.
* Include a mechanism for the submitter of the request to also provide the file.
  * Include a file content field (base64-encoded string) as part of your custom request.
  * Or, have the submitting application place the file somewhere that the player can pick it up.
  * If the submitter and player are, say, on the same docker host, sharing a volume path,
  this becomes quite easy.
  * Note that you can expose your applications/systems to more risk with these approaches:
  injection, filling up your space / queues, likely others I haven't thought of, so take care
  to limit (e.g. filesize, velocity) and validate these requests as appropriate.