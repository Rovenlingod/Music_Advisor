package advisor.controller;

import advisor.config.CurrentUser;
import advisor.service.Advisor;
import advisor.service.PagingService;
import advisor.storage.Buffer;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class UserInteractor {

    private static UserInteractor instance = null;
    private final Scanner scanner = new Scanner(System.in);
    private Advisor advisor = Advisor.getInstance();

    private UserInteractor() {
    }

    public static UserInteractor getInstance() {
        if (Objects.isNull(instance)) {
            instance = new UserInteractor();
        }
        return instance;
    }

    public void process() {
        while (true) {
            String[] command = scanner.nextLine().split(" ");
            switch (command[0]) {
                case "auth":
                    try {
                        advisor.auth();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("---SUCCESS---");
                    break;
                case "featured":
                    if (!CurrentUser.getInstance().authorized()) {
                        System.out.println("Please, provide access for application.");
                    } else {
                        System.out.println(advisor.getFeatured());
                    }
                    break;
                case "new":
                    if (!CurrentUser.getInstance().authorized()) {
                        System.out.println("Please, provide access for application.");
                    } else {
                        System.out.println(advisor.getNewReleases());
                    }
                    break;
                case "categories":
                    if (!CurrentUser.getInstance().authorized()) {
                        System.out.println("Please, provide access for application.");
                    } else {
                        System.out.println(advisor.getCategories());
                    }
                    break;
                case "playlists":
                    if (!CurrentUser.getInstance().authorized()) {
                        System.out.println("Please, provide access for application.");
                    } else {
                        Optional<String> categoryName = Arrays.stream(command).skip(1).reduce((a, b) -> a + " " + b);
                        System.out.println(categoryName.isPresent() ? advisor.getPlaylist(categoryName.get().trim()) : "Empty category name.");
                    }
                    break;
                case "exit":
                    System.out.println("---GOODBYE!---");
                    return;
                case "next":
                    if (!CurrentUser.getInstance().authorized()) {
                        System.out.println("Please, provide access for application.");
                    } else {
                        if (Buffer.getInstance().isEmpty()) {
                            System.out.println("Please type new, featured, categories or playlist %name% first");
                        } else {
                            System.out.println(PagingService.getInstance().next());

                        }
                    }
                    break;
                case "prev":
                    if (!CurrentUser.getInstance().authorized()) {
                        System.out.println("Please, provide access for application.");
                    } else {
                        if (Buffer.getInstance().isEmpty()) {
                            System.out.println("Please type new, featured, categories or playlist %name% first");
                        } else {
                            System.out.println(PagingService.getInstance().prev());
                        }
                    }
                    break;
                default:
                    System.out.println("No such command");
                    break;
            }
        }
    }
}
