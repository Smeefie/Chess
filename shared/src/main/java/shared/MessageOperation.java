package shared;

public class MessageOperation
{
    private MessageOperationType operation;
    private String property;
    private String content;

    public MessageOperationType getOperation()
    {
        return operation;
    }

    public void setOperation(MessageOperationType operation)
    {
        this.operation = operation;
    }

    public String getProperty()
    {
        return property;
    }

    public void setProperty(String property)
    {
        this.property = property;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }
}
