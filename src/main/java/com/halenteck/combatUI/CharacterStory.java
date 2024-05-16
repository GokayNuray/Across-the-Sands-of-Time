package com.halenteck.combatUI;

import javax.swing.*;
import java.awt.*;

    public class CharacterStory extends JDialog {
    public CharacterStory(int characterIndex) {
        setSize(650, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

        JLabel characterName = new JLabel();
        JTextArea characterStory = new JTextArea();
        switch (characterIndex) {
            case 0:
                characterName.setText("Grok");
                characterStory.setText("Grok awoke to a harsh breeze, exhaustion gnawing at him after weeks on the road. The once-lush environment, ravaged by cold weather, offered no easy prey. Hunting had become a desperate struggle. The need for a safe haven outweighed the comfort of staying put. Despite the lack of a clear destination, Grok rose, forced to move on.\n" +
                        "\n" +
                        "A snowstorm soon descended, its fury escalating rapidly. Grok, cursing his misfortune, ran for shelter, the blizzard's white shroud hindering his vision.  Weakened by hunger, he collapsed, hope dwindling. Yet, a dark spot in the snow offered a glimmer of possibility. Ignoring the inexplicable lack of snow cover, Grok mustered his remaining strength and crawled towards it, a potential refuge from the storm and perhaps even sustenance. Tears of joy streamed down his face as warmth enveloped him upon entering. Sleep soon claimed him, a much-needed reprieve before facing the unknown future.");
                break;
            case 1:
                characterName.setText("Kai");
                characterStory.setText("Kai watched in horror as the Black Death ravaged his sister. Logic dictated escape, but they were each other's only family. Her weak pleas to leave him be fell on deaf ears. Fury surged through Kai - his kind, honest sister didn't deserve this fate. The disease took all without mercy.\n" +
                        "\n" +
                        "Suddenly, an anomaly appeared - a strange object unlike anything he knew. Numbers and a glowing surface hinted at some unknown purpose. Could this be a divine answer? A desperate hope flared - maybe a cure for his sister lay within. Ignoring her protests, Kai entered the object, plunging into darkness with only a sliver of hope clinging to him.");
                break;
            case 2:
                characterName.setText("Euobard");
                characterStory.setText("Euobard stared, transfixed by the strange device. Unseen by others, a camp guard's yell jolted him back to grim reality. Work or punishment awaited. He obeyed, the horrors of Mengele and the gas chambers fresh in his mind. A woman's scream - another victim dragged away - fueled his despair. Memories of a happier life, stolen by war, flooded his mind - playing with his sister, his father's bike lessons. Tears streamed down his face, the device a catalyst for this emotional storm?\n" +
                        "\n" +
                        "Reaching the barracks, the device vanished - a hallucination brought on by exhaustion and despair. Yet, as he settled down, it reappeared! He peered through, seeing numbers and dates. A time machine? Before guards arrived for their rounds, a decision loomed. The unknown dangers were irrelevant - anything was better than this unending hell.\n" +
                        "\n" +
                        "A shout. A guard. Euobard didn't hesitate. Stepping through the device, the door slammed shut. He was free, at least for now. The future remained uncertain, but Euobard embraced the unknown, escaping the horrors of Auschwitz.");
                break;
            case 3:
                characterName.setText("Lyra");
                characterStory.setText("Strangled by pollution, Lyra gasped for breath, a fleeting freedom in a dying world. Memories of water and thriving ecosystems fueled her despair. The cracked earth mirrored her broken world. Rage welled up - a curse for ancestors who choked the future. Exhaustion closed in, hallucinations forming a black device. A final act of defiance - a punch at the symbol of destruction. The world dissolved as the device, impossibly real, slammed shut. A time machine? A flicker of hope - a chance to undo the past's mistakes. Or oblivion. Either way, escape from a desolate present. Unconsciousness claimed her, a defiant spirit swallowed by the unknown.");
                break;
            case 4:
                characterName.setText("Esther");
                characterStory.setText("Esther scoffed at the so-called scientists clinging to failed experiments, hiding behind the charade of danger. Progress demanded risk, something Hearken Ahmet Celeb understood when he defied gravity. Ten grueling years later, Esther stood before her completed time machine - a testament to her unwavering defiance.\n" +
                        "\n" +
                        "Pride battled caution - the machine was untested for past travel. Punching in a one-year jump, Esther initiated the process. Alarms blared, dates flickering wildly on the screen - 32 BC, 1939, 1558... Panic surged. The malfunctioning machine defied her control. The door cycled open and shut. Desperate to shut it down, Esther lunged toward the power switch, but the machine lurched forward, catapulting her into the unknown time tunnel.");
                break;
        }
        characterName.setFont(new Font("Arial", Font.BOLD, 30));
        characterStory.setFont(new Font("Arial", Font.PLAIN, 20));
        characterStory.setEditable(false);
        characterStory.setBackground(Color.LIGHT_GRAY);
        characterStory.setLineWrap(true);
        characterStory.setWrapStyleWord(true);
        add(characterName, BorderLayout.NORTH);
        add(characterStory, BorderLayout.CENTER);
        toFront();
        setLocationRelativeTo(null); // center the dialog

    }

}
