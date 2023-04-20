package me.klb.thehypixelpitsurvival.other;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.checkerframework.checker.nullness.qual.NonNull;

public class TitleBuilder {
    public static void showMyTitle(final @NonNull Audience target) {
        final Component mainTitle = Component.text("This is the main title", NamedTextColor.WHITE);
        final Component subtitle = Component.text("This is the subtitle", NamedTextColor.GRAY);

        // Creates a simple title with the default values for fade-in, stay on screen and fade-out durations
        final Title title = Title.title(mainTitle, subtitle);

        // Send the title to your audience
        target.showTitle(title);
    }
}