package com.kanjisoup.audio.event.sdk.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PlayEvent {
  private String uuid;
  private String filePath;
  private String duration;
}
