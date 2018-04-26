//
//  MyProfileVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 3/5/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit
import XLPagerTabStrip
import KSToastView

class MyProfileVC: ButtonBarPagerTabStripViewController {

    @IBOutlet weak var img : UIImageView!
    
//    @IBOutlet weak var shadowView: UIView!
    let blueInstagramColor = Theme.Color.red
//    UIColor(red: 37/255.0, green: 111/255.0, blue: 206/255.0, alpha: 1.0)
    var currentPage : Int = 0
    var firstTime : Bool = true
    
    override func viewDidLoad() {
        // change selected bar color
        settings.style.buttonBarBackgroundColor = .white
        settings.style.buttonBarItemBackgroundColor = .white
        settings.style.selectedBarBackgroundColor = blueInstagramColor
        settings.style.buttonBarItemFont = .boldSystemFont(ofSize: 14)
        settings.style.selectedBarHeight = 2.0
        settings.style.buttonBarMinimumLineSpacing = 0
        settings.style.buttonBarItemTitleColor = Theme.Color.darkGrey
        settings.style.buttonBarItemsShouldFillAvailableWidth = true
        settings.style.buttonBarLeftContentInset = 0
        settings.style.buttonBarRightContentInset = 0
        
        changeCurrentIndexProgressive = { [weak self] (oldCell: ButtonBarViewCell?, newCell: ButtonBarViewCell?, progressPercentage: CGFloat, changeCurrentIndex: Bool, animated: Bool) -> Void in
            guard changeCurrentIndex == true else { return }
            oldCell?.label.textColor = .black
            newCell?.label.textColor = self?.blueInstagramColor
        }
        super.viewDidLoad()
        
        self.title = "My Profile".localized
        
        let me = User.getCurrentUser()
        if me.personal_image != nil && !me.personal_image.isEmpty{
            Provider.sd_setImage(img, urlString: me.personal_image)
        }

        
        configureNavigationBar()
        
        Provider.setScreenName("MyProfileActivity")
    }
    
    
    @IBAction func openEditProfile(){
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "EditProfileVC") as! EditProfileVC
        vc.myProfile = self
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    
    
    func configureNavigationBar() {
        //setup back button
        self.navigationItem.hidesBackButton = false
        let rr = UIBarButtonItem(title: "", style: UIBarButtonItemStyle.plain, target: nil, action:nil)
        self.navigationItem.backBarButtonItem = rr
    }

    
    override func viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()
        
        if firstTime{
            self.firstTime = false
            self.moveToViewController(at: currentPage, animated: false)
        }
    }
    
    
    func showErrorMessage(text: String) {
        KSToastView.ks_showToast(text, duration: 3)
        
        let me = User.getCurrentUser()
        if me.personal_image != nil && !me.personal_image.isEmpty{
            Provider.sd_setImage(img, urlString: me.personal_image)
        }

    }
    
    // MARK: - PagerTabStripDataSource
    
    override func viewControllers(for pagerTabStripController: PagerTabStripViewController) -> [UIViewController] {
        let child_1 = self.storyboard?.instantiateViewController(withIdentifier: "MyAdsVC") as! MyAdsVC
        let child_2 = self.storyboard?.instantiateViewController(withIdentifier: "MyFavoritesVC") as! MyFavoritesVC
        let child_3 = self.storyboard?.instantiateViewController(withIdentifier: "MyChatsVC") as! MyChatsVC
        
        return [child_1, child_2,child_3]
    }

}
