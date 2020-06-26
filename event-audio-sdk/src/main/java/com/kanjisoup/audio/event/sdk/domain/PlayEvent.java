package com.kanjisoup.audio.event.sdk.domain;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Builder
@Data
public class PlayEvent {
  @NotBlank
  private String uuid;
  @NotBlank
  private String filePath;
  private String duration;
}
