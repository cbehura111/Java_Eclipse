package com.chinmay.thrillio;

import com.chinmay.thrillio.partner.Shareable;
import java.util.Iterator;

import com.chinmay.thrillio.constants.KidFriendlyStatus;
import com.chinmay.thrillio.constants.UserType;
import com.chinmay.thrillio.controllers.BookmarkController;
import com.chinmay.thrillio.entities.Bookmark;
import com.chinmay.thrillio.entities.User;

public class View {
	public static void browse(User user, Bookmark[][] bookmarks) {
		System.out.println("\n" + user.getEmail() + " is browsing");
		int bookmarkCount = 0;

		for (Bookmark[] bookmarklist : bookmarks) {
			for (Bookmark bookmark : bookmarklist) {
				// Bookmarking...!!!
				if (bookmarkCount < DataStore.USER_BOOKMARK_LIMIT) {
					boolean isBookmarked = getBookmarkDecesion(bookmark);
					if (isBookmarked) {
						bookmarkCount++;
						BookmarkController.getInstance().saveUserBookmark(user, bookmark);

						System.out.println("New Item Bookmarked --" + bookmark);
					}
				}

				if (user.getUserType().equals(UserType.EDITOR) || user.getUserType().equals(UserType.CHIEF_EDITOR)) {

					// Mark as KidFriendly
					if (bookmark.isKidFriendlyEligible()
							&& bookmark.getKidFriendlyStatus().equals(KidFriendlyStatus.UNKNOWN)) {
						String kidFriendlyStatus = getKidFriendlyStatusDecesion(bookmark);
						if (!kidFriendlyStatus.equals(KidFriendlyStatus.UNKNOWN)) {
							BookmarkController.getInstance().setKidFriendlyStatus(user, kidFriendlyStatus, bookmark);
						}

					}
					
					// Sharing Feature
					if (bookmark.getKidFriendlyStatus().equals(KidFriendlyStatus.APPROVED) && bookmark instanceof Shareable) {
						boolean isShared = getShareDecesion();
						if (isShared) {
							BookmarkController.getInstance().share(user, bookmark);
						}
					}
				}
			}
		}

	}
	
	//TODO: Will take input from user after IO Chapter
	
	private static boolean getShareDecesion() {
		return Math.random() < 0.5 ? true : false;

	}

	private static String getKidFriendlyStatusDecesion(Bookmark bookmark) {
		return Math.random() < 0.4 ? KidFriendlyStatus.APPROVED
				: (Math.random() >= 0.4 && Math.random() < 0.8) ? KidFriendlyStatus.REJECTED
						: KidFriendlyStatus.UNKNOWN;

	}

	private static boolean getBookmarkDecesion(Bookmark bookmark) {
		return Math.random() < 0.5 ? true : false;

	}

	/*
	 * public static void bookmark(User user,Bookmark[][] bookmarks) {
	 * System.out.println("\n"+user.getEmail()+" is browsing"); for(int
	 * i=0;i<DataStore.USER_BOOKMARK_LIMIT;i++) { int typeOffset =
	 * (int)(Math.random() * DataStore.BOOKMARK_TYPE_COUNT); int bookmarkOffset =
	 * (int)(Math.random() * DataStore.BOOKMARK_COUNT_PERTYPE);
	 * 
	 * Bookmark bookmark = bookmarks[typeOffset][bookmarkOffset];
	 * 
	 * BookmarkController.getInstance().saveUserBookmark(user,bookmark);
	 * 
	 * System.out.println(bookmark); }
	 */
}
