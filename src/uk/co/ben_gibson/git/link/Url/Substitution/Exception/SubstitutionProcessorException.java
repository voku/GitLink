package uk.co.ben_gibson.git.link.Url.Substitution.Exception;

import uk.co.ben_gibson.git.link.Exception.Codes;
import uk.co.ben_gibson.git.link.Exception.GitLinkException;

public class SubstitutionProcessorException extends GitLinkException
{
    private SubstitutionProcessorException(String message, int code)
    {
        super(message, code);
    }


    public static SubstitutionProcessorException cannotCreateUrlFromTemplate(String template, String invalidUrl)
    {
        return new SubstitutionProcessorException(
            String.format("Invalid URL (%s) generated using template (%s)", invalidUrl, template),
            Codes.URL_FACTORY_CANNOT_CREATE_URL
        );
    }
}
