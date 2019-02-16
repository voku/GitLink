package uk.co.ben_gibson.git.link.Url.Substitution;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.co.ben_gibson.git.link.Git.Branch;
import uk.co.ben_gibson.git.link.Git.Commit;
import uk.co.ben_gibson.git.link.Git.Exception.RemoteException;
import uk.co.ben_gibson.git.link.Git.File;
import uk.co.ben_gibson.git.link.Git.Remote;
import uk.co.ben_gibson.git.link.Url.Substitution.Exception.SubstitutionProcessorException;

import java.net.MalformedURLException;
import java.net.URL;

public class URLTemplateProcessor
{
    public URL process(
        @NotNull String template,
        @NotNull Remote remote,
        @NotNull Commit commit,
        @Nullable File file
    ) throws RemoteException, SubstitutionProcessorException
    {
        return this.process(template, remote, commit, null, file);
    }


    public URL process(
        @NotNull String template,
        @NotNull Remote remote,
        @NotNull Branch branch,
        @NotNull File file
    ) throws RemoteException, SubstitutionProcessorException
    {
        return this.process(template, remote, null, branch, file);
    }


    private URL process(
        @NotNull String template,
        @NotNull Remote remote,
        @Nullable Commit commit,
        @Nullable Branch branch,
        @Nullable File file
    ) throws RemoteException, SubstitutionProcessorException
    {
        String processedTemplate = this.processRemote(template, remote);

        processedTemplate = this.processCommit(processedTemplate, commit);
        processedTemplate = this.processBranch(processedTemplate, branch);
        processedTemplate = this.processFile(processedTemplate, file);
        processedTemplate = processedTemplate.replace("//", "/");

        try {
            return new URL(processedTemplate);
        } catch (MalformedURLException e) {
            throw SubstitutionProcessorException.cannotCreateUrlFromTemplate(processedTemplate, template);
        }
    }


    private String processFile(@NotNull String template, @Nullable File file)
    {
        template = template.replace("{file:name}", (file != null) ? file.name() : "");
        template = template.replace("{file:path}", (file != null) ? file.directoryPath() : "");

        return template;
    }


    private String processBranch(@NotNull String template, @Nullable Branch branch)
    {
        return template.replace("{branch}", (branch != null) ? branch.toString() : "");
    }



    private String processRemote(@NotNull String template, @NotNull Remote remote) throws RemoteException
    {
        URL remoteURL = remote.url();

        template = template.replace("{remote:url}", remoteURL.toString());
        template = template.replace("{remote:url:path}", remoteURL.getPath());

        return template;
    }


    private String processCommit(@NotNull String template, @Nullable Commit commit)
    {
        template = template.replace("{commit}", (commit != null) ? commit.hash() : "");
        template = template.replace("{commit:short}", (commit != null) ? commit.shortHash() : "");

        return template;
    }
}
