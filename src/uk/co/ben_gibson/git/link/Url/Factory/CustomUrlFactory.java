package uk.co.ben_gibson.git.link.Url.Factory;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.co.ben_gibson.git.link.Git.*;
import uk.co.ben_gibson.git.link.Git.Exception.RemoteException;
import uk.co.ben_gibson.git.link.UI.LineSelection;
import uk.co.ben_gibson.git.link.Url.Factory.Exception.UrlFactoryException;
import uk.co.ben_gibson.git.link.Url.Substitution.Exception.SubstitutionProcessorException;
import uk.co.ben_gibson.git.link.Url.Substitution.URLTemplateProcessor;
import java.net.URL;

public class CustomUrlFactory extends AbstractUrlFactory
{
    private URLTemplateProcessor urlTemplateProcessor;
    private String fileAtBranchUrlTemplate;
    private String fileAtCommitUrlTemplate;
    private String commitUrlTemplate;


    public CustomUrlFactory(
        URLTemplateProcessor urlTemplateProcessor,
        String fileAtBranchUrlTemplate,
        String fileAtCommitUrlTemplate,
        String commitUrlTemplate
    ) {
        this.urlTemplateProcessor    = urlTemplateProcessor;
        this.fileAtBranchUrlTemplate = fileAtBranchUrlTemplate;
        this.fileAtCommitUrlTemplate = fileAtCommitUrlTemplate;
        this.commitUrlTemplate       = commitUrlTemplate;
    }


    public URL createUrlToCommit(@NotNull Remote remote, @NotNull Commit commit) throws UrlFactoryException, RemoteException
    {
        try {
            return this.urlTemplateProcessor.process(this.commitUrlTemplate, remote, commit, null);
        } catch (SubstitutionProcessorException e) {
            throw UrlFactoryException.cannotCreateUrl("Blahh");
        }
    }


    public URL createUrlToFileOnBranch(
        @NotNull Remote remote,
        @NotNull File file,
        @NotNull Branch branch,
        @Nullable LineSelection lineSelection
    ) throws UrlFactoryException, RemoteException {
        try {
            return this.urlTemplateProcessor.process(this.fileAtBranchUrlTemplate, remote, branch, file);
        } catch (SubstitutionProcessorException e) {
            throw UrlFactoryException.cannotCreateUrl("Blahh");
        }
    }


    public URL createUrlToFileAtCommit(
        @NotNull Remote remote,
        @NotNull File file,
        @NotNull Commit commit,
        @Nullable LineSelection lineSelection
    ) throws UrlFactoryException, RemoteException {
        try {
            return this.urlTemplateProcessor.process(this.fileAtCommitUrlTemplate, remote, commit, file);
        } catch (SubstitutionProcessorException e) {
            throw UrlFactoryException.cannotCreateUrl("Blahh");
        }
    }


    public boolean supports(RemoteHost host) {
        return host.isCustom();
    }
}
