package rxn.logger;

/**
 * A log service to allow application log different level of messages.
 *
 * <p>We will capture message data in a POJO instead of direct parameters like many other logger
 * implementation. This might seem verbose at first, but it provides many benefits because a Message
 * may have many optional data to be logged. This design can keep this interface clean. You may use
 * Message.msg() factory method to shorten the code.
 *
 * @author zedeng
 */
public interface Log {
  public void error(Message message);

  public void warn(Message message);

  public void info(Message message);

  public void debug(Message message);

  public void trace(Message message);
}
