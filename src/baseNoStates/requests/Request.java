package baseNoStates.requests;

import org.json.JSONObject;

public interface Request {
  JSONObject answerToJson();
  void process();
}
