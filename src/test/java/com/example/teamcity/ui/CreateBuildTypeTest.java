package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.ui.pages.BuildTypePage;
import com.example.teamcity.ui.pages.admin.CreateBuildTypePage;
import org.testng.annotations.Test;

import static com.example.teamcity.api.enums.Endpoint.PROJECTS;
import static com.example.teamcity.api.generators.TestDataGenerator.generate;

@Test(groups = {"Regression"})
public class CreateBuildTypeTest extends BaseUiTest {
    private static final String REPO_URL = "https://github.com/vikuliyana/TeamCity-NoBugs.git";

    @Test(description = "User should be able to create build configuration", groups = {"Positive"})
    public void userCreatesBuildType() {
        loginAs(testData.getUser());
        var createdProject = superUserCheckRequests.<Project>getRequest(PROJECTS).create(generate(Project.class));

        CreateBuildTypePage.open(createdProject.getId())
                .createForm(REPO_URL)
                .setupBuildType(testData.getBuildType().getName());

        var createdBuildType = superUserCheckRequests.<BuildType>getRequest(Endpoint.BUILD_TYPES).read("name:" + testData.getBuildType().getName());
        softy.assertNotNull(createdBuildType);

        BuildTypePage.open(createdBuildType.getId())
                .title.shouldHave(Condition.text(testData.getBuildType().getName()));
    }

    @Test(description = "User should not be able to create build configuration with an empty name", groups = {"Negative"})
    public void userCreatesProjectWithoutName() {
        loginAs(testData.getUser());
        var createdProject = superUserCheckRequests.<Project>getRequest(PROJECTS).create(generate(Project.class));

        CreateBuildTypePage.open(createdProject.getId())
                .createForm(REPO_URL)
                .setupBuildTypeWithEmptyName()
                .buildTypeNameValidationError.shouldHave(Condition.exactText("Build configuration name must not be empty"));
    }
}
