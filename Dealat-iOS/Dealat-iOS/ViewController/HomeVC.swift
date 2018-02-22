//
//  HomeVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/6/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit
import SideMenu
import CHIPageControl

class HomeVC: BaseVC {
    
    @IBOutlet weak var tableView : UITableView!
    @IBOutlet weak var collectionView : UICollectionView!
    @IBOutlet weak var pageControl : CHIPageControlAji!
    
    var x1 = -1
    var timer : Timer = Timer()
    
    var commericals : [String]{
        var ar = [String]()
        ar.append("ad_image_39")
        ar.append("ad_image_40")
        ar.append("ad_image_42")
        ar.append("ad_image_43")
        return ar
    }
    
    var cats  : [Cat] = [Cat]()
    /*{
     
     let c1 = Cat(JSON: ["category_name" : "Vehicles","mobile_image" : "Sc01 - cat"])
     
     c1?.children = [Cat(JSON: ["category_name" : "Hyundai"])!,
     Cat(JSON: ["category_name" : "BMW"])!,
     Cat(JSON: ["category_name" : "Audi"])!,
     Cat(JSON: ["category_name" : "Volkswagen"])!,
     Cat(JSON: ["category_name" : "Mercedes-Benz"])!
     ]
     
     let c2 = Cat(JSON: ["category_name" : "Real estate","mobile_image" : "real-estats"])
     
     let c3 = Cat(JSON: ["category_name" : "Mobile Phones","mobile_image" : "mobile"])
     
     let c4 = Cat(JSON: ["category_name" : "Furniture","mobile_image" : "furn"])
     
     let c5 = Cat(JSON: ["category_name" : "Kids","mobile_image" : "toy"])
     
     return [c1!,c2!,c3!,c4!,c5!]
     }*/
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        getData()
        setupSideMenuNavController()
    }
    
    override func setupViews() {
        self.tableView.delegate = self
        self.tableView.dataSource = self
        self.tableView.rowHeight = UITableViewAutomaticDimension
        self.tableView.estimatedRowHeight = 100
        self.tableView.addSubview(ref)
        
        collectionView.dataSource = self
        collectionView.delegate = self
        
        pageControl.numberOfPages = self.commericals.count
        self.timer.invalidate()
        self.timer = Timer.scheduledTimer(timeInterval: 3, target: self, selector: #selector(self.rotate), userInfo: nil, repeats: true)
        self.timer.fire()
        
        
        self.setupSearchBar()
    }
    
    override func getRefreshing() {
        Communication.shared.get_all { (res) in
            self.hideLoading()
            self.cats = res
            self.tableView.reloadData()
        }
        
        //        Communication.shared.get_nested_categories { (res) in
        //            self.hideLoading()
        //            self.cats = res
        //            self.tableView.reloadData()
        //        }
    }
    
    @IBAction func sellAction(){
        //TODO
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "NewAddBaesVC") as! NewAddBaesVC
        self.navigationController?.pushViewController(vc, animated: true)
        
        
        //        let vc = self.storyboard?.instantiateViewController(withIdentifier: "ChooseCatVC") as! ChooseCatVC
        //        vc.homeVC = self
        //
        //        vc.modalPresentationStyle = .overCurrentContext
        //        vc.modalTransitionStyle = .crossDissolve
        //        self.present(vc, animated: true, completion: nil)
    }
    
    
    @objc func rotate(){
        let cnt = self.commericals.count
        
        if(cnt > 1){
            if self.x1 != -1 {
                x1 = self.pageControl.currentPage + 1
            }else{
                self.x1 = 0
            }
        }
        else if (cnt == 1 || x1 > cnt - 1) {
            x1 = 0;
        }
        
        let u = x1 % cnt
        
        
        self.collectionView.setContentOffset(CGPoint(x: self.collectionView.frame.size.width * CGFloat(u), y: 0) , animated: true)
    }
    
    func setupSideMenuNavController(){
        let menuController = self.storyboard?.instantiateViewController(withIdentifier: "SideMenuVC") as! SideMenuVC
        
        menuController.homeVC = self
        let menuLeftNavigationController = UISideMenuNavigationController(rootViewController: menuController)
        menuLeftNavigationController.isNavigationBarHidden = true
        
        if Provider.isArabic{
            menuLeftNavigationController.leftSide = false
            SideMenuManager.default.menuRightNavigationController = menuLeftNavigationController
        }else{
            menuLeftNavigationController.leftSide = true
            SideMenuManager.default.menuLeftNavigationController = menuLeftNavigationController
            
        }
        
        SideMenuManager.default.menuWidth = UIScreen.main.bounds.width * 2 / 3
        SideMenuManager.default.menuAllowPushOfSameClassTwice = false
        SideMenuManager.default.menuPresentMode = .menuSlideIn // YAHYA
        SideMenuManager.default.menuAnimationPresentDuration = 0.25 // YAHYA
        
        SideMenuManager.default.menuAddScreenEdgePanGesturesToPresent(toView: self.view)
        SideMenuManager.default.menuAnimationBackgroundColor = UIColor.clear
    }
    
    
    @IBAction func addSideMenuNavController()
    {
        if Provider.isArabic{
            present(SideMenuManager.default.menuRightNavigationController!, animated: true, completion: nil)
        }else{
            present(SideMenuManager.default.menuLeftNavigationController!, animated: true, completion: nil)
        }
    }
    
    override func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
        searchBar.resignFirstResponder()
        if let searchBarText = searchBar.text{
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "AdsListVC") as! AdsListVC
            vc.type = 1
            Provider.searchText = searchBarText
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    
}

extension HomeVC : UITableViewDelegate,UITableViewDataSource{
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.cats.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: _HomeCell, for: indexPath) as! HomeCell
        
        cell.tag = indexPath.row
        cell.cat = self.cats[indexPath.row]
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return UIScreen.main.bounds.width * 477 / 1128
    }
    
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        if self.cats[indexPath.row].children.count > 1{
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "ChooseCatVC") as! ChooseCatVC
            vc.homeVC = self
            vc.cat = self.cats[indexPath.row]
            
            vc.modalPresentationStyle = .overCurrentContext
            vc.modalTransitionStyle = .crossDissolve
            self.present(vc, animated: true, completion: nil)
        }else{
            
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "AdsListVC") as! AdsListVC
            vc.cat = self.cats[indexPath.row]
            self.navigationController?.pushViewController(vc, animated: true)
            
        }
        
    }
    
    
}


extension HomeVC : UICollectionViewDataSource, UICollectionViewDelegate,UICollectionViewDelegateFlowLayout{
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return 4
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "cell", for: indexPath) as! CommericalCell
        cell.imageName = self.commericals[indexPath.row]
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize.init(width: collectionView.frame.width, height: collectionView.frame.height)
    }
    
    //Delegate With ScrollView (scroll item photos)
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if scrollView == self.collectionView{
            if self.commericals.count > 0 {
                if self.collectionView.visibleCells.count > 0{
                    let pp = round(self.collectionView.contentOffset.x / self.collectionView.frame.size.width)
                    self.pageControl.set(progress: Int(pp), animated: true)
                    
                    self.timer.invalidate()
                    self.timer = Timer.scheduledTimer(timeInterval: 3, target: self, selector: #selector(self.rotate), userInfo: nil, repeats: true)
                }
            }
        }
    }
    
    
    
}
